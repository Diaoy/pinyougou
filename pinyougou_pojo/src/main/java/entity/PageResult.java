package entity;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {

    private long pages;//总页数
    private List<T> rows;//当前页结果

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
