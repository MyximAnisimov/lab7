package server;

import common.utility.CustomConsole;
import common.utility.response.Response;
import common.utility.UserIDNumber;
import common.utility.VisibilityArgument;
import common.utility.requests.Request;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public abstract class UDPserver {
    private final InetSocketAddress addr;
    private final CommandExecutor commandExecutor;
    private boolean running = true;
    public static UserIDNumber user_id = new UserIDNumber(0, 0);
    private final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
private Map<Integer, Pair<Byte[], SocketAddress>> synchColl1 = Collections.synchronizedMap(new HashMap<>());
private Map<Integer, Pair<Response, SocketAddress>> synchColl2 = Collections.synchronizedMap(new HashMap<>());
    public UDPserver(InetSocketAddress addr, CommandExecutor commandExecutor) {
        this.addr = addr;
        this.commandExecutor = commandExecutor;
    }

    public InetSocketAddress getAddr() {
        return addr;
    }

    public abstract Pair<Byte[], SocketAddress> receiveData();

    public abstract void sendData(byte[] data, SocketAddress addr);

    public abstract void connectToClient(SocketAddress addr);

    public abstract void disconnectFromClient();
    public abstract void close();


    public void run() throws ExecutionException, InterruptedException {

               System.out.println("Сервер начал свою работу!");
        AtomicReference<Pair<Byte[], SocketAddress>> dataPair = new AtomicReference<>();

        while (running) {
               ArrayList<Pair<Byte[], SocketAddress>> array = new ArrayList<>();
Future task = ForkJoinPool.commonPool().submit(()->{
               array.add(receiveData());
               synchColl1.put(1, array.get(0));
               array.clear();
return null;});
task.get();

               AtomicReference<Request> request = new AtomicReference<>();
               Thread thr;
               thr = new Thread(() -> {
                   var dataFromClient = synchColl1.get(1).getLeft();
                   var clientAddr = synchColl1.get(1).getRight();
                   connectToClient(clientAddr);
                   CustomConsole.printLn("Соединено с " + clientAddr);
                   request.set(SerializationUtils.deserialize(ArrayUtils.toPrimitive(dataFromClient)));
                   CustomConsole.printLn("Обработка " + request + " из " + clientAddr);
                 Future task2 = cachedThreadPool.submit(()->{
                   Response response;
                   response = commandExecutor.handle(request.get());
                   Pair<Response, SocketAddress> pair = new ImmutablePair<>(response, clientAddr);
                   synchColl2.put(1, pair);
                   var pair2 = synchColl2.get(1);
                   var response2 = pair2.getLeft();
                   var addr2 = pair2.getRight();
                   synchColl2.clear();
                   synchColl1.clear();
                   var data = SerializationUtils.serialize(response2);
                   CustomConsole.printLn("Ответ: " + response2);
//                new Thread(() -> {

                   sendData(data, addr2);
                   CustomConsole.printLn("Отправлен ответ клиенту " + addr2);
                   disconnectFromClient();
                    System.out.println("Отключение от клиента");
                    });
                   try {
                       task2.get();
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   } catch (ExecutionException e) {
                       throw new RuntimeException(e);
                   }
               });
               synchronized (thr) {
thr.run();
//               if (afterHook != null) afterHook.run();
////                   });
////               cachedThreadPool.execute(()->{while(true) {
//
////               try{
//
//                   var pair2 = synchColl2.get(1);
//
//                   var response2 = pair2.getLeft();
//                   var addr2 = pair2.getRight();
//                   var data = SerializationUtils.serialize(response2);
//                   CustomConsole.printLn("Ответ: " + response2);
//
//                   sendData(data, addr2);
//                   CustomConsole.printLn("Отправлен ответ клиенту " + addr2);
//
//                   disconnectFromClient();
//                   CustomConsole.printLn("Отключение от клиента " + addr2);
               }

           }
   close();}


    public void stop() {
        running = false;
    }

}

