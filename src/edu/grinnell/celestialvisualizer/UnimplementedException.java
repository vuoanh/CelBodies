package edu.grinnell.celestialvisualizer;

/**
 * Fired whenever an unimplemented method (that ought to be implemented)
 * is invoked.
 */
public class UnimplementedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public UnimplementedException(String msg) {
        super(msg);
    }
}
