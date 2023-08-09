package org.example.chapter3;

/**
 After overwriting the equals method, there are some issues regarding overwriting the hashCode method:
    1. The hashCode method from the Object class calculates the hash code based on the memory address of the object,
        resulting in an integer value. However, it's important to note that the memory address of the same object may change each time the program is run,
        leading to different hashCode values.
    2. When overwriting the equals method, it is necessary to also overwrite the hashCode method.
        Before overwriting, the equals method would determine if two objects are equal based on their memory addresses.
        Thus, the hashCode values would be the same for these objects.
        After overwriting the equals method to use a different approach for determining equality,
        the hashCode method should also be updated to ensure consistency with the equals method.
    3. The hashCode method is required for supporting hash tables, such as those provided by the java.util.HashMap class,
        which rely on hash codes to efficiently locate and store objects.

 my translattion (with some problems)
    after overwrite equals method, some issue about overwriting hashcode method
        1. the return value of hashcode method from Object class is calculating with memory address,
            and with some mechanism it return a integer value.
            note: every time you run the program, the address of the same object will change, so hashcode value will change;
        2. after overwrite the equals method, why should overwrite hashcode method?
            cause we use equals method to estimate whether two object is equals, before overwriting, it estimate two objects by their memory address,
            so hashcode values are the same, when overwriting the equals method, we use another way to estimate the equals of two objects,
            so to correspoding with equals method, we should overwrite hashcode
        3. why we need thid hashcode?
            let have a look at the doc of hashcode method from Oject class
            "Returns a hash code value for the object.
            This method is supported for the benefit of hash tables such as those provided by java.util.HashMap"
 */
public class Code11 {

    public static class OverwriteHashcode{
        private int id;
        private String name;
    }

    public static void main(String[] args) {
        OverwriteHashcode entity = new OverwriteHashcode();
        System.out.println("hashcode = "+entity.hashCode());
    }
}
