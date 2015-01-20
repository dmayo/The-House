package runner;

public class StringEncode {

    public static String encodeInt(int numToEncode){
        String encodedNum="";
        char[] binNum = Integer.toBinaryString(numToEncode).toCharArray();
        String totalByteNum="";
        for (int i=0;i<binNum.length;i+=8){
            String byteNum = "";
            if(binNum.length-1-i-8<0){
                byteNum = new String(binNum, 0, 9+binNum.length-1-i-8);
            }
            else{
                byteNum = new String(binNum, binNum.length-1-i-7, 8);
            }
            totalByteNum=byteNum+totalByteNum;
            char[] charNum = Character.toChars(Integer.parseInt(byteNum, 2));
            
            encodedNum=charNum[0]+encodedNum;
        }
        return encodedNum;
    }
    
    public static int decodeInt(String encodedNum){
        char[] numBytes = encodedNum.toCharArray();
        String totalBinNum = "";
        boolean first=true;
        for( int charByte: numBytes){
            String binString=Integer.toBinaryString(charByte);
            while(binString.length()<8&&!first){
                binString="0"+binString;
            }
            first=false;
            totalBinNum+=binString;
        }
        int decoded = Integer.parseInt(totalBinNum, 2);
        return decoded;
    }
}