/* 
 * Mitchell Alley
 */
int cacheArray[3000][500];
int main(int argc, char *argv[]) {
        for (int cols = 0; cols < 500; cols++){
                for (int rows = 0; rows < 3000; rows++){
                        cacheArray[rows][cols] = rows + cols;
                }
        }
}
