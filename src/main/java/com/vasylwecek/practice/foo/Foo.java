package foo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Foo {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition firstMethodCalled = lock.newCondition();
    private final Condition secondMethodCalled = lock.newCondition();
    private boolean isFirstCalled = false; // флаг
    private boolean isSecondCalled = false; // флаг

    public void first() {
        lock.lock(); // занимаем монитор
        try {
            System.out.print("first"); // вывод
            isFirstCalled = true; // ли закончил
            firstMethodCalled.signal(); // даем сигнал дальше
        } finally {
            lock.unlock();
        }
    }

    public void second() {
        lock.lock();
        try {
            while (!isFirstCalled) {
                firstMethodCalled.await(); // ждем
            }
            System.out.print("second");
            isSecondCalled = true; // чек
            secondMethodCalled.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void third() {
        lock.lock();
        try {
            while (!isSecondCalled) {
                secondMethodCalled.await();
            }
            System.out.print("third");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Foo foo = new Foo();

        Thread t1 = new Thread(foo::second);
        Thread t2 = new Thread(foo::first);
        Thread t3 = new Thread(foo::third);

        t1.start();
        t2.start();
        t3.start();
    }
}
