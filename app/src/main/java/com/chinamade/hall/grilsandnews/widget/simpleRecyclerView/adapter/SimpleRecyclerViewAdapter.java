package com.chinamade.hall.grilsandnews.widget.simpleRecyclerView.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by dayaa on 15/9/25.
 */
public abstract class SimpleRecyclerViewAdapter<T> extends RecyclerView.Adapter {

    private List<T> mObjects;
    private Context context;

    private Object mItemType;
    private AdapterEventDelegate eventDelegate;

    private VuHolderUtil vuHolderUtil;

    /**
     * Lock used to modify the content of {@link #mObjects}. Any write operation
     * performed on the array should be synchronized on this lock.
     */
    private final Object mLock = new Object();

    /**
     * Indicates whether or not {@link #notifyDataSetChanged()} must be called whenever
     * {@link #mObjects} is modified.
     */
    private boolean mNotifyOnChange = true;

    protected ArrayList<SpecialItemVu> headers = new ArrayList<>();
    protected ArrayList<SpecialItemVu> footers = new ArrayList<>();

    public interface SpecialItemVu {
        View onCreateVu(ViewGroup parent);

        void onVuBind(View itemVu, int position);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnNoMoreListener {
        void onNoMore();
    }

    public interface OnErrorListener {
        void onError();
    }

    public SimpleRecyclerViewAdapter(Context context) {
        this.context = context;
        this.mObjects = new ArrayList<T>();
    }

    public SimpleRecyclerViewAdapter(Context context, List<T> mData) {
        this.context = context;
        this.mObjects = mData;
    }

    private View createSpecialViewByType(ViewGroup parent, int viewType) {
        for (SpecialItemVu headerView : headers) {
            if (headerView.hashCode() == viewType) {
                return headerView.onCreateVu(parent);
            }
        }
        for (SpecialItemVu footerView : footers) {
            if (footerView.hashCode() == viewType) {
                return footerView.onCreateVu(parent);
            }
        }
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = createSpecialViewByType(parent, viewType);
        if (view == null) {
            return new SimpleRecyclerVuHolder<T>(parent, getItemViewModel(mItemType));
        } else {
            return new RecyclerView.ViewHolder(view) {
            };
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setId(position);
        if (headers.size() != 0 && position < headers.size()) {
            headers.get(position).onVuBind(holder.itemView, position);//the position of header
            return;
        }

        int i = position - headers.size() - mObjects.size();//the position of footer
        if (footers.size() != 0 && i >= 0) {
            footers.get(i).onVuBind(holder.itemView, i);
            return;
        }
        ((SimpleRecyclerVuHolder) holder).getVuModel().updateViews(mObjects.get(position - headers.size()), position - headers.size());
    }

    /**
     * Use {@link #getItemViewType} instead.
     *
     * @param position position
     * @return itemViewType
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    @Override
    public int getItemViewType(int position) {
        if (headers.size() != 0) {
            if (position < headers.size())
                return headers.get(position).hashCode();
        }
        if (footers.size() != 0) {
            /*
            eg:
            0:header1
            1:header2   2
            2:object1
            3:object2
            4:object3
            5:object4
            6:footer1   6(position) - 2 - 4 = 0
            7:footer2
             */
            int i = position - headers.size() - mObjects.size();
            if (i >= 0) {
                return footers.get(i).hashCode();
            }
        }
        mItemType = getItemViewType(mObjects.get(position - headers.size()));
        if (vuHolderUtil == null) {
            vuHolderUtil = new VuHolderUtil();
        }
        return vuHolderUtil.getIntType(mItemType);
    }

    public Object getItemViewType(T t) {
        return null;
    }

    @Override
    public int getItemCount() {
        return mObjects.size() + headers.size() + footers.size();
    }

    public int getCount() {
        return mObjects.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addHeaderView(@NonNull SpecialItemVu itemVu) {
        if (itemVu == null)
            throw new NullPointerException("the specialItemVu header can not null");
        headers.add(itemVu);
    }

    public void addFooterView(@NonNull SpecialItemVu itemVu) {
        if (itemVu == null)
            throw new NullPointerException("the specialItemVu footer can not null");
        footers.add(itemVu);
    }

    public void removeHeader(SpecialItemVu itemVu) {
        headers.remove(itemVu);
    }

    public void removeHeader(int position) {
        if (position >= headers.size())
            throw new IndexOutOfBoundsException("the position is out of headers' bounds");
        headers.remove(position);
    }

    public void removeFooter(SpecialItemVu itemVu) {
        footers.remove(itemVu);
    }

    public void removeFooter(int position) {
        if (position >= footers.size())
            throw new IndexOutOfBoundsException("the position is out of footers' bounds");
        footers.remove(position);
    }

    public Context getContext() {
        return context;
    }

    protected AdapterEventDelegate getEventDelegate() {
        if (eventDelegate == null)
            eventDelegate = new DefaultAdapterEventDelegate(this);
        return eventDelegate;
    }

    public void stopMore() {
        if (eventDelegate == null)
            throw new NullPointerException("did you invoked the method setLoadMore() ?");
        eventDelegate.stopLoadMore();
    }

    public void pauseMore() {
        if (eventDelegate == null)
            throw new NullPointerException("did you invoked the method setLoadMore() ?");
        eventDelegate.pauseLoadMore();
    }

    public void resumeMore() {
        if (eventDelegate == null)
            throw new NullPointerException("did you invoked the method setLoadMore() ?");
        eventDelegate.resumeLoadMore();
    }

    public View setMoreView(final int res) {
        FrameLayout container = new FrameLayout(getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(getContext()).inflate(res, container);
        getEventDelegate().setMoreView(container);
        return container;
    }


    public View setMoreView(final View view) {
        getEventDelegate().setMoreView(view);
        return view;
    }

    public void addOnLoadMoreListener(OnLoadMoreListener listener) {
        getEventDelegate().addOnLoadMoreListener(listener);
    }

    public View setErrorMoreView(final int res) {
        FrameLayout container = new FrameLayout(getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(getContext()).inflate(res, container);
        getEventDelegate().setErrorMoreView(container);
        return container;
    }

    public View setErrorMoreView(final View view) {
        getEventDelegate().setErrorMoreView(view);
        return view;
    }

    public void addOnErrorListener(OnErrorListener listener) {
        getEventDelegate().addOnErrorListener(listener);
    }

    public View setNoMore(final int res) {
        FrameLayout container = new FrameLayout(getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(getContext()).inflate(res, container);
        getEventDelegate().setNoMoreView(container);
        return container;
    }

    public View setNoMore(final View view) {
        getEventDelegate().setNoMoreView(view);
        return view;
    }

    public void addOnNoMoreListener(OnNoMoreListener listener) {
        getEventDelegate().addOnNoMoreListener(listener);
    }

    public List<T> getDataList() {
        return mObjects;
    }

    /**
     * Control whether methods that change the list ({@link #add},
     * {@link #remove}, {@link #clear}) automatically call
     * {@link #notifyDataSetChanged}.  If set to false, caller must
     * manually call notifyDataSetChanged() to have the changes
     * reflected in the attached view.
     * <p/>
     * The default is true, and calling notifyDataSetChanged()
     * resets the flag to true.
     *
     * @param notifyOnChange if true, modifications to the list will
     *                       automatically call {@link
     *                       #notifyDataSetChanged}
     */
    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }

    public void add(@NonNull T elem) {
        if (eventDelegate != null)
            eventDelegate.onDataAdded(elem == null ? 0 : 1);
        if (elem != null) {
            synchronized (mLock) {
                mObjects.add(elem);
            }
        }
        notifyDataSetChanged();
    }

    public void insert(T object, int index) {
        synchronized (mLock) {
            mObjects.add(index, object);
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public void addAll(@NonNull List<T> elements) {
        if (eventDelegate != null)
            eventDelegate.onDataAdded(elements == null ? 0 : elements.size());
        if (elements != null && elements.size() != 0) {
            synchronized (mLock) {
                mObjects.addAll(elements);
            }
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    public void set(T oldElem, T newElem) {
        synchronized (mLock) {
            set(mObjects.indexOf(oldElem), newElem);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    public void set(int index, T elem) {
        synchronized (mLock) {
            mObjects.set(index, elem);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    public void remove(@NonNull T elem) {
        if (elem != null)
            synchronized (mLock) {
                mObjects.remove(elem);
            }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public void remove(int index) {
        if (index >= mObjects.size() || index < 0) {
            Log.e(getClass().getSimpleName(), "the index is out of array bounds");
        } else {
            synchronized (mLock) {
                mObjects.remove(index);
            }
            if (mNotifyOnChange) notifyDataSetChanged();
        }
    }

    public void refresh(List<T> datas) {
        if (eventDelegate != null)
            eventDelegate.onDataAdded(datas == null ? 0 : datas.size());
        if (datas == null) {
            datas = new ArrayList<T>(0);
        }
        this.mObjects = datas;
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    public void replaceAll(List<T> elements) {
        if (eventDelegate != null)
            eventDelegate.onDataAdded(elements == null ? 0 : elements.size());
        synchronized (mLock) {
            mObjects.clear();
            mObjects.addAll(elements);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    public boolean contains(T elem) {
        return mObjects.contains(elem);
    }


    public void clear() {
        if (eventDelegate != null) eventDelegate.onDataCleared();
        synchronized (mLock) {
            mObjects.clear();
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objects contained
     *                   in this adapter.
     */
    public void sort(Comparator<? super T> comparator) {
        synchronized (mLock) {
            Collections.sort(mObjects, comparator);
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public abstract
    @NonNull
    SimpleRecyclerVuModel<T> getItemViewModel(Object type);

    public static class VuHolderUtil<T> {
        private SparseArray<T> typeSArr = new SparseArray<>();

        public int getIntType(T t) {
            int index = typeSArr.indexOfValue(t);
            if (index == -1) {
                index = typeSArr.size();
                typeSArr.put(index, t);
            }
            return index;
        }
    }

    protected static class SimpleRecyclerVuHolder<T> extends RecyclerView.ViewHolder {

        private SimpleRecyclerVuModel<T> vuModel;

        protected SimpleRecyclerVuHolder(ViewGroup parent, SimpleRecyclerVuModel<T> vModel) {
            super(LayoutInflater.from(parent.getContext()).inflate(vModel.getLayoutResId(), parent, false));
            vuModel = vModel;
            vuModel.bindViews(itemView);
            vuModel.setViews();
        }

        protected SimpleRecyclerVuModel<T> getVuModel() {
            return vuModel;
        }

    }

}
