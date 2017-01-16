package com.chinamade.hall.grilsandnews.data.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */

public class GanIo {
     private boolean error;
     private List<Meizhi> results;

     public boolean isError() {
          return error;
     }

     public void setError(boolean error) {
          this.error = error;
     }

     public List<Meizhi> getResults() {
          return results;
     }

     public void setResults(List<Meizhi> results) {
          this.results = results;
     }


}
