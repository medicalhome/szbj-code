package com.founder.cdr.batch.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TelnetConsole
{
    private static final String END_LINE = "\r\n";

    private int port = 8009;

    private ServerSocket server;

    private PrintWriter writer;

    private BufferedReader reader;

    private int exitCode = 0;

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public void init() throws Exception
    {
        server = new ServerSocket();
        SocketAddress endpoint = new InetSocketAddress("127.0.0.1", port);
        server.bind(endpoint);
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                System.out.println("Stop application.");
            }
        });
    }

    public void start()
    {
        while (exitCode < 2)
        {
            exitCode = 0;
            try
            {
                Socket socket = server.accept();
                writer = new PrintWriter(new OutputStreamWriter(
                        socket.getOutputStream()));
                // writer.write("欢迎进入Batch管理控制台!   ");
                writer.write("Welcome to Batch management console!");
                writer.write(END_LINE);
                writer.flush();

                reader = new BufferedReader(new InputStreamReader(
                        socket.getInputStream(), "UTF-8"));
                String line = "";
                while ((exitCode < 1) && (line = reader.readLine()) != null)
                {
                    line = line.trim();
                    exitCode = process(line);
                }
                reader.close();
                writer.close();
                socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private int process(String line)
    {
        System.out.println(line);

        if ("quit".equalsIgnoreCase(line) || "exit".equalsIgnoreCase(line))
        {
            // writer.write("再见！");
            writer.write("Bye bye!");
            writer.write(END_LINE);
            writer.flush();
            return 1;
        }
        else if ("shutdown".equalsIgnoreCase(line))
        {
            // writer.write("开始停止Batch服务......");
            writer.write("Stopping Batch service......");
            writer.write(END_LINE);
            writer.flush();
            writer.write("Bye bye!");
            writer.write(END_LINE);
            writer.flush();
            return 2;
        }

        return 0;
    }

    private void terminate()
    {
        try
        {
            server.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception
    {
        TelnetConsole console = new TelnetConsole();
        console.init();
        console.start();
        console.terminate();
    }
}
