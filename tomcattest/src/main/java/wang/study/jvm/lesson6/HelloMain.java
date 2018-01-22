package wang.study.jvm.lesson6;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloMain {
    private Method method;
    private Object worker;

    public HelloMain(Object worker){
        this.setWorker(worker);
    }

    private void setWorker(Object worker) {
        this.worker = worker;
        try {
            this.method = worker.getClass().getMethod("doit",new Class[0]);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(true){
                    try {
                        method.invoke(worker);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final byte[] newclass = IOUtils.toByteArray(new ClassPathResource("static/Worker.txt").getInputStream());
        ClassLoader loader = new MyClassLoader(this.getClass().getClassLoader(),newclass);
        Class clazz = loader.loadClass("wang.study.jvm.lesson6.Worker");
        this.setWorker(clazz.newInstance());


    }
    static class MyClassLoader extends ClassLoader{
        private ClassLoader parent;
        private byte[] newclass;
        public MyClassLoader(ClassLoader classLoader,byte[] newclass) {
            parent = classLoader;
            this.newclass = newclass;
        }

        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            System.out.println("load class"+name);
            if(name.equals("wang.study.jvm.lesson6.Worker")){
                System.out.println("load class use custom loader");
                return this.defineClass(name,newclass,0,newclass.length);
            }
            return parent.loadClass(name);

        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        new HelloMain(new Worker()).run();
    }
}
