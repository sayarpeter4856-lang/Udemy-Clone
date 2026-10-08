import {BrowserRouter, Route, Routes} from "react-router-dom";
import HeaderComponents from "./component/HeaderComponents.tsx";
import type {ReactNode} from "react";
import {isLoggedIn} from "./auth/service/AuthService.ts";
import Login from "./auth/component/Login.tsx";
import Home from "./student/components/Home.tsx";
import TeacherDashboard from "./teacher/TeacherDashboard.tsx";
import TeacherHomeDashboard from "./teacher/component/TeacherHomeDashboard.tsx";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import Register from "./auth/component/Register.tsx";
import CartContextProvider from "./cart/CartContextProvider.tsx";
import CartView from "./cart/components/CartView.tsx";
import EnrolledCourseComponents from "./student/components/EnrolledCourseComponents.tsx";
import CourseTableComponent from "./course/component/CourseTableComponent.tsx";
import CreateLessonComponent from "./teacher/component/CreateLessonComponent.tsx";

function AuthGuard({children}: { children: ReactNode }) {
    const beLogin = isLoggedIn();
    console.log("BeLogin:", beLogin);
    if (beLogin)
        return <>{children}</>
    return <Login/>
}

const queryClient = new QueryClient({
    defaultOptions: {
        queries: {
            refetchOnWindowFocus: false
        }
    }
});

function App() {

    // const beTeacher = getRoleName() === 'ROLE_TEACHER';

    return (
        <>
            <BrowserRouter>
                <QueryClientProvider client={queryClient}>
                    <CartContextProvider>
                        <HeaderComponents/>
                        <Routes>
                            <Route path="/" element={
                                <AuthGuard>
                                    <Home/>
                                </AuthGuard>
                            }/>
                            <Route path="/cart" element={
                                <AuthGuard>
                                    <CartView/>
                                </AuthGuard>
                            }/>
                            <Route path="/enroll-courses" element={
                                <AuthGuard>
                                    <EnrolledCourseComponents/>
                                </AuthGuard>
                            }/>
                            <Route path="/register" element={<
                                Register/>}/>
                            <Route path="/login" element={<Login/>}/>
                            <Route path="/teacher" element={
                                <AuthGuard>
                                    {
                                        <TeacherDashboard/>
                                    }
                                </AuthGuard>
                            }>
                                <Route path="" element={<TeacherHomeDashboard/>}/>
                                <Route path="create-course" element={<CourseTableComponent/>}/>
                                <Route path="create-lesson/:id" element={<CreateLessonComponent/>}/>
                            </Route>
                        </Routes>
                    </CartContextProvider>
                </QueryClientProvider>
            </BrowserRouter>
        </>
    );
}

export default App;