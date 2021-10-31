export const needZeroAtEnd = (number) => {
    let decimalPart = (number + "").split(".")[1];
    
    if(decimalPart.toString().split("")){
        return true;
    }

    return false;

}