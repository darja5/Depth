Data depth project was written while learning about Data Depth as a part of a subject called
Probabilidad y estadistica on Facultad de Cantabria, Spain.

Convex-hull peeling depth is one of many data depth measures and we chose it because of some
previous knowledge on convex-hulls and its algorithms.

Program reads a .txt files where every line represents one point with x and y value seperated with a single space.
Points in the the file represent out data set. Points have to be put in order based on x values. 
We later calculate convex hull (upper and lower one) with an incremental algorithm and a function
called isRight(). The function returns one if input points represent a right turn. 
Procedure is repeated as there are still points on which we cann build a convex-hull. 

Output is the same set of points just ordered by layers or convex-hull numbers.