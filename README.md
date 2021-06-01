# MetOpt
### Лабораторная работа №3

Работа выполнена группой:
* Дзюба Мария M3235
* Карасева Екатерина M3235
* Рындина Валерия M3235

### [Отчет](МетодыОптимизации.pdf)

### Методы
* [Прямой метод на основе LU разложения](src/src/method/LUMethod.java)
* [Метод Гаусса](src/src/method/GaussMethod.java)
* [Бонус: метод сопряженных градиентов()](src/src/method/ConjugateMethod.java)

### Виды матриц
* [Профильный](src/src/matrix/ProfileSLAEMatrix.java)
* [Бонус: разреженный строчно- столбцовый](src/src/matrix/SparseSLAEMatrix.java)

### Генераторы матриц
* [Матрица, число обусловленности которой регулируется за счет изменения диагонального преобладания](src/src/generator/Generator2.java)
* [Матрица Гильберта](src/src/generator/Generator3.java)
* [Бонус: матрица с диагональным проебладанием](src/src/generator/Generator52.java)
* [Бонус: матрица с обратным знаком внедиагональных элементов](src/src/generator/Generator53.java)
