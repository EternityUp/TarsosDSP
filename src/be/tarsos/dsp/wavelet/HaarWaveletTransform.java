package be.tarsos.dsp.wavelet;

public class HaarWaveletTransform {
		
	/**
	 * Does an in-place Haar wavelet transform. The
	 * length of data needs to be a power of two.
	 * It is based on the algorithm found in "Wavelets Made Easy" by Yves Nivergelt, page 24.
	 * @param s The data to transform.
	 */
	public void transform(float[] s){
		int m = s.length;
		assert isPowerOfTwo(m);
		int n = log2(m);
		int j = 2;
		int i = 1;
		for(int l = 0 ; l < n ; l++ ){
			m = m/2;
			for(int k=0; k < m;k++){
				float a = (s[j*k]+s[j*k + i])/2.0f;
				float c = (s[j*k]-s[j*k + i])/2.0f;
				s[j*k] = a;
				s[j*k+i] = c;
			}
			i = j;
			j = j * 2;
		}
	}
	
    /**
     * Does an in-place inverse Haar Wavelet Transform. The data needs to be a power of two.
     * It is based on the algorithm found in "Wavelets Made Easy" by Yves Nivergelt, page 29.
     * @param data The data to transform.
     */
    public void inverseTransform(float[] data){
    	int m = data.length;
		assert isPowerOfTwo(m);
		int n = log2(m);
		int i = pow2(n-1);
		int j = 2 * i;
		m = 1;
		for(int l = n ; l >= 1; l--){
			for(int k = 0; k < m ; k++){
				float a = data[j*k]+data[j*k+i];
				float a1 = data[j*k]-data[j*k+i];
				data[j*k] = a;
				data[j*k+i] = a1;
			}
			j = i;
			i = i /2;
			m = 2*m;
		}
	}
    
    
	   
	/**
	 * Checks if the number is a power of two. For performance it uses bit shift
	 * operators. e.g. 4 in binary format is
	 * "0000 0000 0000 0000 0000 0000 0000 0100"; and -4 is
	 * "1111 1111 1111 1111 1111 1111 1111 1100"; and 4&-4 will be
	 * "0000 0000 0000 0000 0000 0000 0000 0100";
	 * 
	 * @param number
	 *            The number to check.
	 * @return True if the number is a power of two, false otherwise.
	 */
	private static boolean isPowerOfTwo(int number) {
		if (number <= 0) {
			throw new IllegalArgumentException("number: " + number);
		}
		if ((number & -number) == number) {
			return true;
		}
		return false;
	}

	/**
	 * A quick and simple way to calculate log2 of integers.
	 * 
	 * @param bits
	 *            the integer
	 * @return log2(bits)
	 */
	private int log2(int bits) {
		if (bits == 0) {
			return 0;
		}
		return 31 - Integer.numberOfLeadingZeros(bits);
	}
	
	/**
	 * A quick way to calculate the power of two (2^power), by using bit shifts.
	 * @param power The power.
	 * @return 2^power
	 */
	private int pow2(int power) {
		return 1<<power;
	}

}