package interfaces;

import java.io.File;

public interface FileHandler<T, K extends Object>{

    void writeObjectToFile(T object);

    T readObjectFromFile(String s);

    K readObjectsFromFile();



}
