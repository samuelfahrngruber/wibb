package at.wibb.server.shared;

public class Preconditions {
    
    private Preconditions() {}

    /**
     * Checks if the provided argument is non-null.
     * 
     * @param <T> The type of the provided value.
     * @param obj The value that the null check should be performed against.
     * @return The object passed as an input argument.
     * @throws NullPointerException If the provided value is null.
     */
    public static <T> T checkNotNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

    /**
     * Checks if the provided expression is met or and throw an {@link IllegalArgumentException} if not.
     * 
     * @param expression The expression that should be evaluated.
     * @throws IllegalArgumentException If the expression evaluated to false.
     */
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }
}
