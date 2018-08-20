package main.java;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DurationCalculator {

  private String[] versions = {"2.5", "x", "2", "1"};
  private String[] layers = {"x", "3", "2", "1"};

  private Map<String, int[]> bitRates = new HashMap<String, int[]>();
  private Map<String, int[]> sampleRates = new HashMap<String, int[]>();
  private Map<String, int[]> samples = new HashMap<String, int[]>();

  public long skipID3(byte[] buffer) {
    int id3v2_flags = 0;
    int footer_size;
    int tag_size;
    if (buffer[0] == 0x49 && buffer[1] == 0x44 && buffer[2] == 0x33) { // 'ID3'
      id3v2_flags = buffer[5];
    }
    if ((id3v2_flags & 0x10) != 0) {
      footer_size = 10;
    } else {
      footer_size = 0;
    }

//    byte z0 = buffer[6];
//    System.out.println("z0+0x80: "+z0+0x80);
//    byte z1 = buffer[7];
//    byte z2 = buffer[8];
//    byte z3 = buffer[9];
    
    int z0 = buffer[6];
    System.out.println("z0+0x80: "+z0+0x80);
    int z1 = buffer[7];
    int z2 = buffer[8];
    int z3 = buffer[9];

    if ((z0 & 0x80) == 0 && (z1 & 0x80) == 0 && (z2 & 0x80) == 0 && (z3 & 0x80) == 0) {
      tag_size =
          ((z0 & 0x7f) * 2097152) + ((z1 & 0x7f) * 16384) + ((z2 & 0x7f) * 128) + (z3 & 0x7f);
      System.out.println("10+tag_size+footer_size: "+10+tag_size+footer_size);
      return 10 + tag_size + footer_size;
    }
    return 0;
  }

  public void setBitRates() {
    bitRates.put("V1Lx", new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    bitRates.put("V1L1",
        new int[] {0, 32, 64, 96, 128, 160, 192, 224, 256, 288, 320, 352, 384, 416, 448});
    bitRates.put("V1L2",
        new int[] {0, 32, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320, 384});
    bitRates.put("V1L3",
        new int[] {0, 32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320});
    bitRates.put("V2Lx", new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    bitRates.put("V2L1",
        new int[] {0, 32, 48, 56, 64, 80, 96, 112, 128, 144, 160, 176, 192, 224, 256});
    bitRates.put("V2L2", new int[] {0, 8, 16, 24, 32, 40, 48, 56, 64, 80, 96, 112, 128, 144, 160});
    bitRates.put("V2L3", new int[] {0, 8, 16, 24, 32, 40, 48, 56, 64, 80, 96, 112, 128, 144, 160});
    bitRates.put("VxLx", new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    bitRates.put("VxL1", new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    bitRates.put("VxL2", new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    bitRates.put("VxL3", new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
  }

  public void setSampleRates() {
    sampleRates.put("x", new int[] {0, 0, 0});
    sampleRates.put("1", new int[] {44100, 48000, 32000});
    sampleRates.put("2", new int[] {22050, 24000, 16000});
    sampleRates.put("2.5", new int[] {11025, 12000, 8000});
  }

  public void setSamples() {
    samples.put("x", new int[] {0, 0, 0, 0});
    samples.put("1", new int[] {0, 348, 1152, 1152});
    samples.put("2", new int[] {0, 348, 1152, 576});
  }


  public int frameSize(int samples, int layer, int bit_rate, int sample_rate, int paddingBit) {
    if (layer == 1) {
      return (((samples * bit_rate * 125 / sample_rate) + paddingBit * 4));
    } else { // layer 2, 3
      return (((samples * bit_rate * 125) / sample_rate) + paddingBit);
    }
  }

  public int[] parseFrameHeader(byte[] header) {
    int b1 = header[1];
    int b2 = header[2];
    String simple_version;
    int bit_rate;
    int sample_rate;
    int sample_rate_idx;
    int sample;
    int padding_bit;

    int version_bits = (b1 & 0x18) >> 3;
    String version = versions[version_bits];
    if (version == "2.5") {
      simple_version = "2";
    } else {
      simple_version = version;
    }

    int layer_bits = (b1 & 0x06) >> 1;
    String layer = layers[layer_bits];

    String bit_rate_key = String.format("%s %s", simple_version, layer);
    int bit_rate_index = (b2 & 0xf0) >> 4;

    if (bitRates.containsKey(bit_rate_key)) {
      bit_rate = bitRates.get(bit_rate_key)[bit_rate_index];
    } else {
      bit_rate = 0;
    }

    sample_rate_idx = (b2 & 0x0c) >> 2;

    if (sampleRates.containsKey(version)) {
      sample_rate = sampleRates.get(version)[sample_rate_idx];
    } else {
      sample_rate = 0;
    }

    sample = samples.get(simple_version)[Integer.parseInt(layer)];

    padding_bit = (b2 & 0x02) >> 1;

    int[] results = {bit_rate, sample_rate,
        frameSize(sample, Integer.parseInt(layer), bit_rate, sample_rate, padding_bit), sample};

    return results;
  }

  public long estimateDuration(int bitRate, int offset, long filesize) {
    float kbps = (bitRate * 1000) / 8;
    double datasize = filesize - offset;

    return roundDuration(datasize / kbps);
  }

  public long roundDuration(double duration) {
    return Math.round(duration * 1000) / 1000; // round to nearest ms
  }

  public long mp3Duration(String filename, boolean cbrEstimate) {
    long duration = 0;
    int[] info = null;
    File f = new File(filename);
    long filesize = f.length();
    System.out.println(filesize);

    byte[] result = null;
    try {
      //InputStream input = new BufferedInputStream(new FileInputStream(f));
      FileInputStream input = new FileInputStream(f);
      result = readAndClose(input);
      for (int i = 0; i < result.length; i++) {
        result[i] = (byte) (result[i] & 0xff);
      }
      String buffer = null;
      for (int i = 0; i < 100; i++) {
        buffer += (byte) (result[i] & 0xff);
      }
      //System.out.println(buffer);
      byte[] buffer1 = buffer.getBytes();
      if (buffer1.length < 100) {
        return 0;
      }
      int offset = 0;
      offset = (int) (skipID3(buffer1)&0xff);

      while (true) {
        //System.out.println(offset);
        //Arrays.binarySearch(result, 0, result.length, offset);
        for (int i = 0; i < 10; i++) {
          
        }
        
        byte[] buffer2 = buffer.getBytes();
        if(buffer2.length < 10) {
          return roundDuration(duration);
        }
        if(buffer2[0] == 0xff && (buffer2[1] & 0xe0) == 0xe0) {
          info = parseFrameHeader(buffer2);
          if(info[2] != 0 && info[3] != 0) {
            offset += info[2];
            duration += ( info[3] / info[1] );
          }
          else {
            offset+=1; //Corrupt file?
          }
        }
        else if(buffer2[0] == 0x54 && buffer2[1] == 0x41 && buffer2[2] == 0x47) { //#TAG'
            offset += 128; //Skip over id3v1 tag size
        }
        else {
            offset+=1; //Corrupt file?
        }
        if(info != null) {
          return roundDuration(estimateDuration(info[0], offset, filesize));
        }
        //System.out.println("Offset: "+offset);
      }
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }

    return roundDuration(duration);
  }

  byte[] readAndClose(InputStream aInput) {
    // carries the data from input to output :
    byte[] bucket = new byte[32 * 1024];
    ByteArrayOutputStream result = null;
    int offset = 0;
    try {
      try {
        // Use buffering? No. Buffering avoids costly access to disk or network;
        // buffering to an in-memory stream makes no sense.
        result = new ByteArrayOutputStream(bucket.length);
        int bytesRead = 0;
        while (bytesRead != -1) {
          // aInput.read() returns -1, 0, or more :
          bytesRead = aInput.read(bucket);
          if (bytesRead > 0) {
            result.write(bucket, 0, bytesRead);
          }
        }
      } finally {
        aInput.close();
        // result.close(); this is a no-operation for ByteArrayOutputStream
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return result.toByteArray();
  }


}

