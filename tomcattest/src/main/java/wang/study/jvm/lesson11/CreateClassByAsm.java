package wang.study.jvm.lesson11;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/1/12.
 */
public class CreateClassByAsm {
    /**
     *  int a=6;
     int b=7;
     int c=(a+b)*3;
     System.out.println(c);
     */

    public void run(){
        int a=6;
        int b=7;
        int c=(a+b)*3;
        System.out.println(c);
    }

    public void createClass() throws Exception {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS| ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V1_7,Opcodes.ACC_PUBLIC,"wang/study/jvm/lesson11/Test",null,"java/lang/Object",new String[]{});
        //默认构造函数
        MethodVisitor cinit = cw.visitMethod(Opcodes.ACC_PUBLIC,"<init>","()V",null,null);
        //从参数表第一个位置I加载this
        cinit.visitVarInsn(Opcodes.ALOAD, 0);
        cinit.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>","()V",false);
        cinit.visitInsn(Opcodes.RETURN);
        //自动计算堆栈、操作数栈大小
        cinit.visitMaxs(0,0);
        cinit.visitEnd();

        MethodVisitor method = cw.visitMethod(Opcodes.ACC_PUBLIC,"run","()V",null,null);
        method.visitVarInsn(Opcodes.ALOAD, 0);
        method.visitIntInsn(Opcodes.BIPUSH,6);
        method.visitVarInsn(Opcodes.ISTORE,1);
        method.visitIntInsn(Opcodes.BIPUSH,7);
        method.visitVarInsn(Opcodes.ISTORE,2);

        method.visitVarInsn(Opcodes.ILOAD,1);
        method.visitVarInsn(Opcodes.ILOAD,2);
        method.visitInsn(Opcodes.IADD);
        method.visitInsn(Opcodes.ICONST_3);
        method.visitInsn(Opcodes.IMUL);
        method.visitVarInsn(Opcodes.ISTORE,3);
        method.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out","Ljava/io/PrintStream;");
        method.visitVarInsn(Opcodes.ILOAD,3);
        method.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/io/PrintStream","println", "(I)V",false);

        method.visitInsn(Opcodes.RETURN);
        method.visitMaxs(0,0);
        method.visitEnd();
        cw.visitEnd();

        //使用自定义类加载器，加载生成类，验证结果
        byte[] data = cw.toByteArray();
        Class clazz = new MyClassLoader(this.getClass().getClassLoader()).defineClass("wang.study.jvm.lesson11.Test",data);
        Method m = clazz.getMethod("run");
        m.invoke(clazz.newInstance());

        this.run();



    }

    public  static void main(String[] args) throws Exception {
        new CreateClassByAsm().createClass();
    }
}
