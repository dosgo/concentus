/* Copyright (c) 2016 Logan Stromberg

   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions
   are met:

   - Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.

   - Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.

   - Neither the name of Internet Society, IETF or IETF Trust, nor the
   names of specific contributors, may be used to endorse or promote
   products derived from this software without specific prior written
   permission.

   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
   ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
   A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
   OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
   EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
   PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
   LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
   NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.concentus;

import java.security.MessageDigest;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.lang.reflect.Field;
class Arrays {

    static int[][] InitTwoDimensionalArrayInt(int x, int y) {
        return new int[x][y];
    }

    static float[][] InitTwoDimensionalArrayFloat(int x, int y) {
        return new float[x][y];
    }

    static short[][] InitTwoDimensionalArrayShort(int x, int y) {
        return new short[x][y];
    }

    static byte[][] InitTwoDimensionalArrayByte(int x, int y) {
        return new byte[x][y];
    }

    static byte[][][] InitThreeDimensionalArrayByte(int x, int y, int z) {
        return new byte[x][y][z];
    }

    static void MemSet(byte[] array, byte value) {
        java.util.Arrays.fill(array, value);
    }

    static void MemSet(short[] array, short value) {
        java.util.Arrays.fill(array, value);
    }

    static void MemSet(int[] array, int value) {
        java.util.Arrays.fill(array, value);
    }

    static void MemSet(float[] array, float value) {
        java.util.Arrays.fill(array, value);
    }

    static void MemSet(byte[] array, byte value, int length) {
        java.util.Arrays.fill(array, 0, length, value);
    }

    static void MemSet(short[] array, short value, int length) {
        java.util.Arrays.fill(array, 0, length, value);
    }

    static void MemSet(int[] array, int value, int length) {
        java.util.Arrays.fill(array, 0, length, value);
    }

    static void MemSet(float[] array, float value, int length) {
        java.util.Arrays.fill(array, 0, length, value);
    }

    static void MemSetWithOffset(byte[] array, byte value, int offset, int length) {
        java.util.Arrays.fill(array, offset, offset + length, value);
    }

    static void MemSetWithOffset(short[] array, short value, int offset, int length) {
        java.util.Arrays.fill(array, offset, offset + length, value);
    }

    static void MemSetWithOffset(int[] array, int value, int offset, int length) {
        java.util.Arrays.fill(array, offset, offset + length, value);
    }

    static void MemMove(byte[] array, int src_idx, int dst_idx, int length) {
        System.arraycopy(array, src_idx, array, dst_idx, length);
    }

    static void MemMove(short[] array, int src_idx, int dst_idx, int length) {
        System.arraycopy(array, src_idx, array, dst_idx, length);
    }

    static void MemMove(int[] array, int src_idx, int dst_idx, int length) {
        System.arraycopy(array, src_idx, array, dst_idx, length);
    }

      public static String generateMD5(byte[] data) {
        try {
            // 获取 MD5 消息摘要实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            // 更新数据
            md.update(data);
            
            // 计算哈希值
            byte[] digest = md.digest();
            
            // 转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xFF));
            }
            return sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            // 处理异常（MD5 算法在所有Java实现中都可用，此异常通常不会发生）
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
     public static String generateMD5(short[] data) {
        try {
            // 获取 MD5 消息摘要实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            // 处理每个 short 值
            for (short num : data) {
                // 将 short 转换为 2 字节大端序表示
                md.update(new byte[] {
                    (byte) (num >> 8), // 高字节
                    (byte) num         // 低字节
                });
            }
            
            // 计算哈希值
            byte[] digest = md.digest();
            
            // 转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xFF));
            }
            return sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
     public static String int16ArrayToMD5(int[] array)  {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 直接处理整数数组，避免中间转换
            for (int num : array) {
                // 每个整数转为4字节大端序
                md.update(new byte[] {
                    (byte)(num >>> 24),
                    (byte)(num >>> 16),
                    (byte)(num >>> 8),
                    (byte)num
                });
            }
            // 生成十六进制MD5
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xFF));
            }
            return sb.toString();
        }catch(Exception e){

        }
        return null;
    }
     public static String intArrayToMD5(int[] array)  {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 直接处理整数数组，避免中间转换
            for (int num : array) {
                // 每个整数转为4字节大端序
                md.update(new byte[] {
                    (byte)(num >>> 24),
                    (byte)(num >>> 16),
                    (byte)(num >>> 8),
                    (byte)num
                });
            }
            // 生成十六进制MD5
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xFF));
            }
            return sb.toString();
        }catch(Exception e){

        }
        return null;
    }
    public static void printObjectFields(Object obj) {
        if (obj == null) {
            System.out.println("Object is null");
            return;
        }

        Class<?> clazz = obj.getClass();
        System.out.println("Fields of class: " + clazz.getName());

        // 获取所有字段（包括私有）
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            try {
                // 设置可访问，以便访问私有字段
                field.setAccessible(true);
                Object value = field.get(obj);
                System.out.println(field.getName() + ": " + value);
            } catch (IllegalAccessException e) {
                System.out.println(field.getName() + ": [Access Exception]");
            }
        }
    }

}
