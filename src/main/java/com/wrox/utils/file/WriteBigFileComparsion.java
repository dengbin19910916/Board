package com.wrox.utils.file;

//        import util.StopWatch;

/**
 * NIO写大文件比较
 * @author Will
 *
 */
/*
public class WriteBigFileComparison {

    // credit chunk be written per time
    private static final int DATA_CHUNK = 128 * 1024 * 1024;

    // total credit size is 2G
    private static final long LEN = 2L * 1024 * 1024 * 1024L;


    public static void writeWithFileChannel() throws IOException {
        File file = new File("e:/test/fc.dat");
        if (file.exists()) {
            file.delete();
        }

        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = raf.getChannel();

        byte[] credit = null;
        long len = LEN;
        ByteBuffer buf = ByteBuffer.allocate(DATA_CHUNK);
        int dataChunk = DATA_CHUNK / (1024 * 1024);
        while (len >= DATA_CHUNK) {
            System.out.println("write a credit chunk: " + dataChunk + "MB");

            buf.clear(); // clear for re-write
            credit = new byte[DATA_CHUNK];
            for (int i = 0; i < DATA_CHUNK; i++) {
                buf.put(credit[i]);
            }

            credit = null;

            buf.flip(); // switches a Buffer from writing mode to reading mode
            fileChannel.write(buf);
            fileChannel.force(true);

            len -= DATA_CHUNK;
        }

        if (len > 0) {
            System.out.println("write rest credit chunk: " + len + "B");
            buf = ByteBuffer.allocateDirect((int) len);
            credit = new byte[(int) len];
            for (int i = 0; i < len; i++) {
                buf.put(credit[i]);
            }

            buf.flip(); // switches a Buffer from writing mode to reading mode, position to 0, limit not changed
            fileChannel.write(buf);
            fileChannel.force(true);
            credit = null;
        }

        fileChannel.close();
        raf.close();
    }

    */
/**
     * write big file with MappedByteBuffer
     * @throws IOException
     *//*

    public static void writeWithMappedByteBuffer() throws IOException {
        File file = new File("e:/test/mb.dat");
        if (file.exists()) {
            file.delete();
        }

        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = raf.getChannel();
        int pos = 0;
        MappedByteBuffer mbb = null;
        byte[] credit = null;
        long len = LEN;
        int dataChunk = DATA_CHUNK / (1024 * 1024);
        while (len >= DATA_CHUNK) {
            System.out.println("write a credit chunk: " + dataChunk + "MB");

            mbb = fileChannel.map(MapMode.READ_WRITE, pos, DATA_CHUNK);
            credit = new byte[DATA_CHUNK];
            mbb.put(credit);

            credit = null;

            len -= DATA_CHUNK;
            pos += DATA_CHUNK;
        }

        if (len > 0) {
            System.out.println("write rest credit chunk: " + len + "B");

            mbb = fileChannel.map(MapMode.READ_WRITE, pos, len);
            credit = new byte[(int) len];
            mbb.put(credit);
        }

        credit = null;
        unmap(mbb);   // release MappedByteBuffer
        fileChannel.close();
    }

    public static void writeWithTransferTo() throws IOException {
        File file = new File("e:/test/transfer.dat");
        if (file.exists()) {
            file.delete();
        }

        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel toFileChannel = raf.getChannel();

        long len = LEN;
        byte[] credit = null;
        ByteArrayInputStream bais = null;
        ReadableByteChannel fromByteChannel = null;
        long position = 0;
        int dataChunk = DATA_CHUNK / (1024 * 1024);
        while (len >= DATA_CHUNK) {
            System.out.println("write a credit chunk: " + dataChunk + "MB");

            credit = new byte[DATA_CHUNK];
            bais = new ByteArrayInputStream(credit);
            fromByteChannel = Channels.newChannel(bais);

            long count = DATA_CHUNK;
            toFileChannel.transferFrom(fromByteChannel, position, count);

            credit = null;
            position += DATA_CHUNK;
            len -= DATA_CHUNK;
        }

        if (len > 0) {
            System.out.println("write rest credit chunk: " + len + "B");

            credit = new byte[(int) len];
            bais = new ByteArrayInputStream(credit);
            fromByteChannel = Channels.newChannel(bais);

            long count = len;
            toFileChannel.transferFrom(fromByteChannel, position, count);
        }

        credit = null;
        toFileChannel.close();
        fromByteChannel.close();
    }

    */
/**
     * 在MappedByteBuffer释放后再对它进行读操作的话就会引发jvm crash，在并发情况下很容易发生
     * 正在释放时另一个线程正开始读取，于是crash就发生了。所以为了系统稳定性释放前一般需要检
     * 查是否还有线程在读或写
     * @param mappedByteBuffer
     *//*

    public static void unmap(final MappedByteBuffer mappedByteBuffer) {
        try {
            if (mappedByteBuffer == null) {
                return;
            }

            mappedByteBuffer.force();
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                @SuppressWarnings("restriction")
                public Object run() {
                    try {
                        Method getCleanerMethod = mappedByteBuffer.getClass()
                                .getMethod("cleaner", new Class[0]);
                        getCleanerMethod.setAccessible(true);
                        sun.misc.Cleaner cleaner =
                                (sun.misc.Cleaner) getCleanerMethod
                                        .invoke(mappedByteBuffer, new Object[0]);
                        cleaner.clean();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("clean MappedByteBuffer completed");
                    return null;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
       */
/* StopWatch sw = new StopWatch();

        sw.startWithTaskName("write with file channel's write(ByteBuffer)");
        writeWithFileChannel();
        sw.stopAndPrint();

        sw.startWithTaskName("write with file channel's transferTo");
        writeWithTransferTo();
        sw.stopAndPrint();

        sw.startWithTaskName("write with MappedByteBuffer");
        writeWithMappedByteBuffer();
        sw.stopAndPrint();*//*

    }

}*/
