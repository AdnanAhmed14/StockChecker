package rtx.stock.checker.action;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class ScheduledTasks {
    private final StockChecking stockChecking;
    @Scheduled(fixedRate = 600000)
    public void reportCurrentTime() throws Exception {
        stockChecking.checkStock();
    }
}
