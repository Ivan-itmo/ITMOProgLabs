package clientutility;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * Кастомный десериализатор, подменяющий serverutility.ServerResponse на clientutility.ServerResponse.
 */
public class CustomObjectInputStream extends ObjectInputStream {
    public CustomObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        if (desc.getName().equals("serverutility.ServerResponse")) {
            return clientutility.ServerResponse.class;
        }
        return super.resolveClass(desc);
    }
}