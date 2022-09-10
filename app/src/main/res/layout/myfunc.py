# function myfunc accepts 2 parameters myset and length and
# returns a new set which contains string of length less than equal to length
def myfunc(myset, length):
    
    # create a new set to store result
    new_set = set()
    
    # traverse myset and check if the element is of type str and its
    # its length is less than or equal to length
    for i in myset:
        if (type(i) == str) and len(i) <= length:
            # add the element if condition satisfy
            new_set.add(i)
    
    # return the new set
    return new_set


# test case 
myset = {1, 2.0, '3', 45, 'a', 'bc', 'xyz'}
assert myfunc(myset, 2) == {'3', 'a', 'bc'}