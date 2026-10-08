import {useGetAllEnrolledCourse} from "../../course/query/CourseHookQuery.ts";
import {getLoggedInUsername} from "../../auth/service/AuthService.ts";
import type {Course} from "../../course/data/Course.ts";

export default function EnrolledCourseComponents() {
    const { data: enrolledCourses, isLoading } = useGetAllEnrolledCourse();

    const username = getLoggedInUsername()!;

    if (isLoading) {
        return (
            <div className="flex justify-center items-center min-h-100">
                <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-indigo-600"></div>
            </div>
        );
    }

    return (
        <div className="container mx-auto px-4 py-8">
            <h2 className="text-2xl font-bold text-gray-800 mb-6"><span>{username}</span>'s Enrolled Courses</h2>

            {/* Responsive Grid Layout */}
            <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
                {enrolledCourses?.map((course: Course) => (
                    <div
                        key={course.courseId}
                        className="flex flex-col overflow-hidden rounded-2xl bg-white shadow-md transition-all duration-300 hover:-translate-y-1 hover:shadow-xl border border-gray-100"
                    >
                        {/* Image Container with Padding */}
                        <div className="p-4 bg-gray-50">
                            <div className="aspect-video w-full overflow-hidden rounded-xl bg-gray-200">
                                {course.imageBase64 ? (
                                    <img
                                        src={`data:image/jpeg;base64,${course.imageBase64}`}
                                        alt={course.title}
                                        className="h-full w-full object-cover object-center transition-transform duration-300 hover:scale-105"
                                    />
                                ) : (
                                    <span className="text-gray-400 text-sm">No Preview Available</span>
                                )}
                            </div>
                        </div>

                        {/* Content Area */}
                        <div className="flex flex-1 flex-col p-5">
                            {/* Category Badge */}
                            <span className="self-start rounded-full bg-blue-50 px-2.5 py-1 text-xs font-semibold text-blue-600 uppercase tracking-wider">
                                {course.categoryName}
                            </span>

                            {/* Title & Description */}
                            <h3 className="mt-3 text-lg font-bold text-gray-900 line-clamp-1">
                                {course.title}
                            </h3>
                            <p className="mt-2 text-sm text-gray-500 line-clamp-2 flex-1">
                                {course.description}
                            </p>

                            {/* Divider */}
                            <hr className="my-4 border-gray-100" />

                            {/* Footer: Instructor & Price */}
                            <div className="flex items-center justify-between">
                                <div className="flex flex-col">
                                    <span className="text-xs text-gray-400">Instructor</span>
                                    <span className="text-sm font-medium text-gray-700">{course.teacherName}</span>
                                </div>
                                <div>
                                    <button className="bg-indigo-600 text-white px-4 py-2 rounded-md">Learn</button>
                                </div>
                                <div className="text-right">
                                    <span className="text-xl font-black text-gray-900">{course.fees === 0 ? "Free" : `$${course.fees}`}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}