/* 
 * Mitchell Alley
 */
int cacheArray[128][8];
int main(int argc, char *argv[]) {
	for ( int i = 0; i < 100; i++){
        	for (int rows = 0; rows < 128; rows+=64){
                	for (int cols = 0; cols < 8; cols++){
                        	cacheArray[rows][cols] = i + rows + cols;
                	}
        	}
	}
}
