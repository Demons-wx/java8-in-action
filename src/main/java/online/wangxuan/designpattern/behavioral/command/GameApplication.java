package online.wangxuan.designpattern.behavioral.command;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author wangxuan
 * @date 2020/6/6 6:57 PM
 */

public class GameApplication {

    private static final int MAX_HANDLED_REQ_COUNT_PER_LOOP = 100;
    private Queue<Command> queue = new LinkedList<>();

    public void mainLoop() {
        while (true) {
            List<Request> requests = new ArrayList<>();
            // 省略从epoll或者select获取数据，并封装成Request的逻辑，
            // 注意设置超时时间，如果很长时间没有接收到请求，就执行下面的逻辑

            for (Request request : requests) {
                Event event = request.getEvent();
                Command command = null;
                if (event.equals(Event.GOT_DIAMOND)) {
                    command = new GotDiamondCommand();
                } else if (event.equals(Event.GOT_STAR)) {
                    command = new GotStartCommand();
                } else if (event.equals(Event.HIT_OBSTACLE)) {
                    command = new HitObstacleCommand();
                } else if (event.equals(Event.ARCHIVE)) {
                    command = new ArchiveCommand();
                }

                queue.add(command);
            }

            int handledCount = 0;
            while (handledCount < MAX_HANDLED_REQ_COUNT_PER_LOOP) {
                if (queue.isEmpty()) {
                    break;
                }
                Command command = queue.poll();
                command.execute();
                handledCount++;
            }
        }
    }

    class Request {
        private Event event;

        public Event getEvent() {
            return event;
        }
    }

    enum Event {
        GOT_DIAMOND, GOT_STAR, HIT_OBSTACLE, ARCHIVE
    }
}
