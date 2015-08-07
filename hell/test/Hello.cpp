/*
 * Hello.cpp
 *
 *  Created on: 2015-4-11
 *      Author: zhouyueyue
 */

#include "Hello.h"
#include <iostream>
using namespace std;
Hello::Hello() {
	// TODO Auto-generated constructor stub
	cout<<"hello world";
}

Hello::~Hello() {
	// TODO Auto-generated destructor stub
}
void swap(int A[], int i, int j){
	int temp = A[i];
	A[i] = A[j];
	A[j] = temp;
}
int part(int A[], int l, int r, int& pivot){
	while(l < r){
		while(A[++l] < pivot){};
		while(A[--r] > pivot && r > 1){};
		swap(A, l, r);
	}
	return l;
}

void qSort(int A[], int i, int j){
	if(i <= j){
		return;
	}
	int pivot = (i + j)/2;//找到轴值
	swap(A, pivot, j);
	int k = part(A, i-1, j, A[j]);
	qSort(A, i, k-1);
	qSort(A, k+1, j);
}
/**
 * 1.指针的类型:int* pt中指针的类型为int*
 * 2.指针指向的类型:int* pt中指针指向的类型为int
 * 3.指针存储在32为系统中占4个字节,64位占8个字节
 */
int main(){
	char a[50] = "welcome you";
	char* p = a;//将a的地址交给p
	char** pt = &p;//将p的值所在的地址交给pt
	cout<<"**pt= "<<**pt<<endl;
	pt++;//pt++的话,因为指针占4个字节,所以&p移动sizeof(char*) = 4个字节,而存储的值才是指向a的,其值的地址并并不指向a,所以会输出一个不可预知的值
	cout<<"**pt= "<<**pt<<endl;
}
