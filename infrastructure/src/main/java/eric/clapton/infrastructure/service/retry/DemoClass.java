package eric.clapton.infrastructure.service.retry;

import org.springframework.stereotype.Component;

@Component
public class DemoClass {
    @RetryOn(RuntimeException.class)
    public void demoMethod() {
        if (Math.random() > 0.01) {
            throw new RuntimeException("some exception");
        }
        System.out.println("Completed successfully.");
    }
}
