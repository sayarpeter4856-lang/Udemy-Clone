import {CartContext} from "./CartContext.ts";
import type {Course} from "../course/data/Course.ts";
import {useState} from "react";


function CartContextProvider({children}: {children: React.ReactNode}) {
    const [items, setItems] = useState<Course[]>([]);
    const addItem = (course:Course) => {
        if(detectCourseDuplicate(course)) return;
        setItems([...items, course]);
    }
    const totalCost = items.reduce((total, item) => total + item.fees, 0);
    const detectCourseDuplicate = (course:Course) => {
        return items.find(item => item.courseId === course.courseId);
    }
    const removeItem = (id:number) => {
        setItems(prev => prev.filter(item => item.courseId !== id));
    }
    const clearCart = () => {
        setItems([]);
    }
    const value = {
        items,
        addItem,
        removeItem,
        clearCart,
        totalCost
    }
    return (
        <>
            <CartContext.Provider value={value}>
                {children}
            </CartContext.Provider>
        </>
    );
}

export default CartContextProvider;