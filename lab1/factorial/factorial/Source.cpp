#include <iostream>

using namespace std;

int f_factor(int num) {
	return (num != 1) ? (f_factor(num - 1)*num) : 1;
}
