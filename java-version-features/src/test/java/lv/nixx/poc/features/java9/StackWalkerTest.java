package lv.nixx.poc.features.java9;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class StackWalkerTest {

    @Test
    void stackWalkerTest() {

        String message = "Message";

        String msg = StackWalker
                .getInstance()
                .walk((Stream<StackWalker.StackFrame> frames) -> {
                    StackWalker.StackFrame frame = frames.skip(0).findFirst().get();
                    return frame.getClassName() + "."
                            + frame.getMethodName() + "("
                            + frame.getFileName() + ":"
                            + frame.getLineNumber() + ") "
                            + message;
                });

        System.out.println(msg);
    }

}
