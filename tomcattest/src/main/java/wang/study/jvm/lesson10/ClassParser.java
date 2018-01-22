package wang.study.jvm.lesson10;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.*;

public class ClassParser {
    public static final int INTEFACE_TAG = 8;


    public ClassParser(InputStream input){
        this.input = input;
    }

    public void parse() throws IOException {
        //读取魔法数
        byte[] magic = this.nextByte(4);

        //读取版本号
        byte[] version = this.nextByte(4);

        //读取常量池的长度
        int staticLength = this.nextInt(2);
        System.out.println("static length is "+staticLength);
        //读取常量,常量池索引从1开始
        for(int i=1;i<staticLength;i++){
            this.readStatic(i);
        }
        for(int index : staticPool.keySet()){
            Metadata metadata = this.staticPool.get(index);
            //System.out.println(index+" "+metadata.getTag() +" "+ getValue(metadata));
        }

        //access_flag
        int accessFlag = this.nextInt(2);

        //this class
        int classIndex = this.nextInt(2);
        //super class
        int superIndex = this.nextInt(2);
        parents.add(this.staticPool.get(superIndex));

        //interfaces
        int interfaceCount = this.nextInt(2);
        for(int i=0;i<interfaceCount;i++){
            int interfaceIndex = this.nextInt(2);
            this.interfaces.add(this.staticPool.get(interfaceIndex));
        }

        //fields
        int fieldCount = this.nextInt(2);
        for(int i=0;i<fieldCount;i++){
            //accessflag
            int af = this.nextInt(2);
            int nameIndex = this.nextInt(2);
            int description = this.nextInt(2);
            int attrCount = this.nextInt(2);
            for(int j=0;j<attrCount;j++){
                this.parseAttribute(getValue(nameIndex));
            }
            this.fields.add(new Metadata(7,nameIndex,description));
        }

        //method
        int methodCount = this.nextInt(2);
        for(int i=0;i<methodCount;i++){
            //accessflag
            int af = this.nextInt(2);
            int nameIndex = this.nextInt(2);
            int description = this.nextInt(2);
            int attrCount = this.nextInt(2);
            System.out.println("attr count "+attrCount+" for "+getValue(nameIndex));
            for(int j=0;j<attrCount;j++){
                this.parseAttribute(getValue(nameIndex));
            }
            this.methods.add(new Metadata(7,nameIndex,description));
        }


    }
    //忽略字段、方法属性
    private void parseAttribute(String name) throws IOException {
        int typeIndex = this.nextInt(2);
        String type = this.getValue(typeIndex);
        int length =  this.nextInt(4);
        System.out.println("skip length "+length+" for "+name);
        this.nextByte(length);
    }

    private void parseInnerClass() throws IOException {
        this.nextByte(2);
        this.nextByte(2);
        this.nextByte(2);
        this.nextByte(2);
    }

    private void parseException() {
    }

    //读取常量
    private void readStatic(int sequence) throws IOException {
        int tag = this.nextInt(1);

        if(tag==1){
            //UTF-8编码字符串
            int length = this.nextInt(2);
            String value = new String(this.nextByte(length));
            staticPool.put(sequence,new Metadata(tag,value));
        }
        else if(tag == 3){
            //整形
            this.nextByte(4);
        }else if(tag == 4){
            //浮点数
            this.nextByte(4);
        }else if(tag == 5){
            //长整型
            this.nextByte(8);
        }else if(tag == 6){
            //双精度
            this.nextByte(8);
        }else if(tag == 7){
            //类或接口
            int index = this.nextInt(2);
            staticPool.put(sequence,new Metadata(tag,index));
        }else if(tag == 8){
            //字符串的字面量
            int i1 = this.nextInt(2);
            staticPool.put(sequence,new Metadata(tag,i1));
        }
        //字段 &  类中方法 & 接口中方法 & 字段或方法的名称
        else if(tag == 9||tag == 10||tag == 11||tag == 12){
            int i1 = this.nextInt(2);
            int i2 = this.nextInt(2);
            staticPool.put(sequence,new Metadata(tag,i1,i2));
        }
    }


    private int nextInt(int length) throws IOException {
        byte[] data = this.nextByte(length);
        int value =0;
        for(int i=0;i<data.length;i++){
            value = value*256 + data[i];
        }
        return value;
    }
    private byte[] nextByte(int length) throws IOException {
        byte[] data = new byte[length];
        input.read(data);
        return data;
    }


    private String toHexString(byte[] data) {
        StringBuilder builder = new StringBuilder();
        for(byte b : data){
            builder.append( String.format("%02x", b));
        }
        return builder.toString();
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = new ClassPathResource("static/UserTypeController.txt").getInputStream();
        ClassParser parser = new ClassParser(inputStream);
        parser.parse();

        //输出它的所有的字段、方法，超类，实现的接口
        System.out.println("定义的字段：");
        for(Metadata field : parser.getFields() ){
            if(field == null) continue;
            System.out.println(parser.getValue(field.getIndexes()[0])+" "+parser.getValue(field.getIndexes()[1]));
        }

        System.out.println("定义的方法：");
        for(Metadata method : parser.getMethods() ){
            System.out.println(parser.getValue(method.getIndexes()[0]));
        }

        System.out.println("超类：");
        for(Metadata parent : parser.getParents() ){
            System.out.println(parser.getValue(parent.getIndexes()[0]));
        }

        System.out.println("实现的接口");
        for(Metadata inter : parser.getInterfaces() ){
            System.out.println(parser.getValue(inter.getIndexes()[0]));
        }

    }
    private String getValue(Metadata metadata) {
        if(metadata == null) return "";
        if(metadata.getTag() == 1){
            return metadata.getValue();
        }
        return getValue(metadata.getIndexes()[0]);
    }
    private String getValue(int index) {
        Metadata metadata = this.staticPool.get(index);
        return getValue(metadata);
    }

    private InputStream input;

    private Map<Integer,Metadata> staticPool = new HashMap<>();

    private List<Metadata> fields = new ArrayList<>();

    private List<Metadata> methods = new ArrayList<>();

    private List<Metadata> parents = new ArrayList<>();

    private List<Metadata> interfaces = new ArrayList<>();

    public Map<Integer, Metadata> getStaticPool() {
        return staticPool;
    }

    public void setStaticPool(Map<Integer, Metadata> staticPool) {
        this.staticPool = staticPool;
    }

    public List<Metadata> getFields() {
        return fields;
    }

    public void setFields(List<Metadata> fields) {
        this.fields = fields;
    }

    public List<Metadata> getMethods() {
        return methods;
    }

    public void setMethods(List<Metadata> methods) {
        this.methods = methods;
    }

    public List<Metadata> getParents() {
        return parents;
    }

    public void setParents(List<Metadata> parents) {
        this.parents = parents;
    }

    public List<Metadata> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<Metadata> interfaces) {
        this.interfaces = interfaces;
    }
}
