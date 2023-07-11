package server;

import com.google.common.primitives.Bytes;
import common.utility.CustomConsole;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class UDPDatagramServer extends UDPserver{
    private final int PACKET_SIZE = 1024;
    private final int DATA_SIZE = PACKET_SIZE - 1;

    private final DatagramSocket datagramSocket;
    public UDPDatagramServer(InetAddress address, int port, CommandExecutor commandExecutor) throws SocketException {
        super(new InetSocketAddress(address, port), commandExecutor);
        this.datagramSocket = new DatagramSocket(getAddr());
        this.datagramSocket.setReuseAddress(true);
    }

    @Override
    public Pair<Byte[], SocketAddress> receiveData(){
try {
    var received = false;
    var result = new byte[0];
    SocketAddress addr = null;
    ArrayList<Byte[]> array = null;
    while (!received) {
        var buffer = new byte[PACKET_SIZE];
        var datagramPacket = new DatagramPacket(buffer, PACKET_SIZE);
        datagramSocket.receive(datagramPacket);
        addr = datagramPacket.getSocketAddress();
        if (buffer[buffer.length - 1] == 1) {
            received = true;
            CustomConsole.printLn("Получение данных от " + datagramPacket.getAddress() + " окончено");
        }
        result = Bytes.concat(result, Arrays.copyOf(buffer, buffer.length - 1));
        array = new ArrayList<>();
        array.add(ArrayUtils.toObject(result));
    }
    return new ImmutablePair<>(ArrayUtils.toObject(result), addr);
}
catch(IOException e){
    CustomConsole.printError("Ошибка");
}
return null;
    }


   @Override
    public void sendData(byte[] data, SocketAddress addr){
        try{
        byte[][] ret = new byte[(int)Math.ceil(data.length / (double)DATA_SIZE)][DATA_SIZE];

        int start = 0;
        for(int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(data, start, start + DATA_SIZE);
            start += DATA_SIZE;
        }


        for(int i = 0; i < ret.length; i++) {
            var chunk = ret[i];
            if (i == ret.length - 1) {
                var lastChunk = Bytes.concat(chunk, new byte[]{1});
                var dp = new DatagramPacket(lastChunk, PACKET_SIZE, addr);
                datagramSocket.send(dp);
            } else {
                var dp = new DatagramPacket(ByteBuffer.allocate(PACKET_SIZE).put(chunk).array(), PACKET_SIZE, addr);
                datagramSocket.send(dp);
            }
            CustomConsole.printLn("Отправка данных завершена");
        }}
        catch(IOException e){
            System.out.println("Ошибка");
        }}
    @Override
    public void connectToClient(SocketAddress socketAddress) {
        try{
        datagramSocket.connect(socketAddress);}
        catch(SocketException e){
            System.out.println("Ошибка");
        }
    }
    @Override
    public void disconnectFromClient(){
        datagramSocket.disconnect();
    }
    @Override
    public void close() {
        datagramSocket.close();
    }
}
