import axios from "axios";
import {getToken} from "../../auth/service/AuthService.ts";
import type {Course} from "../data/Course.ts";
import type {EnrolledDto} from "../../cart/dto/EnrolledDto.ts";
import type {Lessons} from "../data/Lessons.ts";

axios.interceptors.request.use(
    function (config) {
        const token = getToken();
        if (token) {
            config.headers["Authorization"] = token;
        }
        return config;
    },
    function (error) {
        return Promise.reject(error);
    }
);

const COURSE_URL = "http://localhost:8080/api/courses";

export const getEnrolledCoursesRequest = async (): Promise<Course[]> => {
    const response = await axios.get(`${COURSE_URL}/enroll-courses`);
    return response.data;
};

export const enrollCourseRequest =
    async (courseIds: EnrolledDto): Promise<string> => {
    const response =
        await axios.post(`${COURSE_URL}/enroll`, courseIds);
    return response.data;
};

export const getCourse = async ():Promise<Course[]> => {
    const response = await axios.get(`${COURSE_URL}/teacher`);
    return response.data;
}

export const getAllCoursesRequest = async ():Promise<Course[]> => {
    const response = await axios.get(`${COURSE_URL}`);
    return response.data;
}

export const createCourseRequest =
    async (formData: FormData):Promise<string> => {
    const response = await axios.post(COURSE_URL, formData,{
        headers: {
            "Content-Type": "multipart/form-data",
        },
    });
    return response.data;
}

export const addLessonRequest =
    async (id:number,lesson:Lessons) => {
    const lessonData = {
        lesson_name: lesson.lessonName,
        lesson_linked: lesson.lessonLinked
    };
    const response =
        await axios.post(`${COURSE_URL}/${id}/lessons`, lessonData);
    return response.data;
}

export const getLessonsRequest = async (id: number): Promise<Lessons[]> => {
    const response = await axios.get(`${COURSE_URL}/${id}/lessons`);
    return response.data.map((lesson: { lesson_name: string; lesson_linked: string }) => ({
        lessonName: lesson.lesson_name,
        lessonLinked: lesson.lesson_linked
    }));
}





