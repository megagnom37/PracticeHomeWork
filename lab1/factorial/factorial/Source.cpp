#include <iostream>

using namespace std;

int f_factor(int num) {
	return (num != 1) ? (f_factor(num - 1)*num) : 1;
}

int main(){
	#Test with input 5 and output 120
	if(f_factor(5) == 120) cout<<"Ok"<<endl;
}