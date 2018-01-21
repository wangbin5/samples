package wang.study.jvm.lesson10;

public class Metadata {
    private int tag;
    private int[] indexes;
    private String value;

    public Metadata(int tag,int...indexes){
        this.tag = tag;
        this.indexes = indexes;
    }
    public Metadata(int tag,String value){
        this.tag = tag;
        this.value = value;
    }
    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int[] getIndexes() {
        return indexes;
    }

    public void setIndexes(int[] indexes) {
        this.indexes = indexes;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
