package java3.lesson1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class Box<T extends Fruit> {
    ArrayList<T> box = new ArrayList();

    public Box(T... fruits) {
        box = new ArrayList<>(Arrays.asList(fruits));
    }

    public ArrayList<T> getBox() {
        return box;
    }

    public void addFruit(T fruit) {
        box.add(fruit);
    }

    public float getWeight() {
        float totalWeight = 0.0f;
        for (T t : box) {
            totalWeight = totalWeight + t.getWeight();
        }
        return totalWeight;
    }

    public boolean compare(Box<? extends Fruit> boxWithSomething) {
        return Math.abs(getWeight() - boxWithSomething.getWeight()) < 0.0001f;
    }


    public void moveFruits(Box<T> s) throws WrongOperationException {
        float sum_weights = 0f;
        float a = 0;
        for (T t : box) {
            if (t.getWeight() == 1.0f || t.getWeight() == 1.5f) {
                s.addFruit(t);
                a = t.getWeight();
                sum_weights += a;
            } else {
                throw new WrongOperationException("Wrong object in box");
            }
        }
        float size = 0;
        if (s.getClass().isArray()) {
            size = Array.getLength(s);
        } else if (s instanceof Collection) {
            size = ((Collection<?>) s).size();
        }
        if (Math.abs(size * a - sum_weights) > 0.01f) {
            throw new WrongOperationException("Not equal objects in box");
        }

    }
}
