package wang.study.jvm.tomcattest.model;

import java.util.Date;

public class DateValue {
    private Date date;
    public DateValue(Date date){
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
