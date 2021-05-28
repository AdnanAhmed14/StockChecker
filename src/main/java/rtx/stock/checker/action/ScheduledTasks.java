package rtx.stock.checker.action;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.time.DateUtils.MILLIS_PER_MINUTE;

@Component
@AllArgsConstructor
public class ScheduledTasks {
    private final StockChecking stockChecking;
    @Scheduled(fixedRate = 30 * MILLIS_PER_MINUTE)
    public void reportCurrentTime() throws Exception {
        stockChecking.checkStock();
    }
}
