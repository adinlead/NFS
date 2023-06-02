package cc.itez.nfs.mould;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Outcome {
    private boolean successful;
    private Path path;
    private String cause;
    private Exception exception;

    private static final Map<String, Outcome> CACHED = new HashMap<>();

    public static Outcome failure(String s) {
        return CACHED.computeIfAbsent(s, (k) -> new Outcome().setCause(k));
    }

    public static Outcome error(Exception e) {
        return new Outcome().setCause(e.getMessage()).setException(e);
    }

    public static Outcome success(Path path) {
        return new Outcome().setSuccessful(true).setPath(path);
    }
}
