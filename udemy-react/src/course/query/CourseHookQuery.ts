import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {
    addLessonRequest,
    createCourseRequest,
    getAllCoursesRequest,
    getCourse,
    getEnrolledCoursesRequest, getLessonsRequest
} from "../service/CourseService.ts";
import type {Course} from "../data/Course.ts";
import type {Lessons} from "../data/Lessons.ts";

export function useAllGetCourses() {
    return useQuery<Course[]>({
        queryKey: ["courses"],
        queryFn: getAllCoursesRequest,
        staleTime: 2 * 1000
    });
}

export function useGetCourses() {
    return useQuery<Course[]>({
        queryKey: ["courses"],
        queryFn: getCourse,
        staleTime: 2 * 1000
    });
}

export function useCreateCourse() {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: createCourseRequest,
        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: ["courses"]
            })
        }
    })
}

export function useGetAllEnrolledCourse() {
    return useQuery<Course[]>({
        queryKey: ["enroll-courses"],
        queryFn: getEnrolledCoursesRequest,
        staleTime: 2 * 1000
    })
}

export function useGetCourseLesson(id: number) {
    return useQuery<Lessons[]>({
        queryKey: ["course-lessons", id],
        queryFn: () => getLessonsRequest(id),
        staleTime: 1000 * 5
    });
}

export function useCreateLesson(courseId: number) {
    const queryClient = useQueryClient();

    return useMutation({
        // 1. Pass the lesson payload here so it can be provided dynamically during .mutate()
        mutationFn: (newLesson: Lessons) => addLessonRequest(courseId, newLesson),
        onSuccess: () => {
            // 2. Fix the cache key to match the query we want to refresh
            queryClient.invalidateQueries({
                queryKey: ["course-lessons", courseId]
            });
        }
    });
}