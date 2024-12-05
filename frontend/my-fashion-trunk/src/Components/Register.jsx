import Button from "./Elements/Button.jsx";
import {NavLink} from "react-router";
import Login from "./Login.jsx";

function Register() {
    const briefcaseIcon = <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor"
                               className="size-7">
        <path fillRule="evenodd"
              d="M7.5 5.25a3 3 0 0 1 3-3h3a3 3 0 0 1 3 3v.205c.933.085 1.857.197 2.774.334 1.454.218 2.476 1.483 2.476 2.917v3.033c0 1.211-.734 2.352-1.936 2.752A24.726 24.726 0 0 1 12 15.75c-2.73 0-5.357-.442-7.814-1.259-1.202-.4-1.936-1.541-1.936-2.752V8.706c0-1.434 1.022-2.7 2.476-2.917A48.814 48.814 0 0 1 7.5 5.455V5.25Zm7.5 0v.09a49.488 49.488 0 0 0-6 0v-.09a1.5 1.5 0 0 1 1.5-1.5h3a1.5 1.5 0 0 1 1.5 1.5Zm-3 8.25a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5Z"
              clipRule="evenodd"/>
        <path
            d="M3 18.4v-2.796a4.3 4.3 0 0 0 .713.31A26.226 26.226 0 0 0 12 17.25c2.892 0 5.68-.468 8.287-1.335.252-.084.49-.189.713-.311V18.4c0 1.452-1.047 2.728-2.523 2.923-2.12.282-4.282.427-6.477.427a49.19 49.19 0 0 1-6.477-.427C4.047 21.128 3 19.852 3 18.4Z"/>
    </svg>
    return (
        <div className="flex items-center justify-center h-screen bg-gray-100">
            <div>
                <div className="flex items-center justify-center gap-2 text-2xl mb-7">
                    <span>{briefcaseIcon}</span>
                    <h1>Fashion trunk</h1>
                </div>

                <div className="border-2 rounded-2xl border-gray-300 p-6 shadow-xl bg-white">
                    <form>
                        <div className="mt-4">
                            <label className="form-label">Name</label>
                            <input
                                className="form-input w-full"
                                name="name"
                                type="text"
                                placeholder="Enter name"
                            />
                        </div>
                        <div className="mt-4">
                            <label className="form-label">Surname</label>
                            <input
                                className="form-input w-full"
                                name="surname"
                                type="text"
                                placeholder="Enter surname"
                            />
                        </div>
                        <div className="mt-4">
                            <label className="form-label">Email Address</label>
                            <input
                                className="form-input w-full"
                                name="email"
                                type="email"
                                placeholder="Enter email"
                            />
                        </div>
                        <div className="mt-4">
                            <label className="form-label">Password</label>
                            <input
                                className="form-input w-full"
                                name="password"
                                type="password"
                            />
                        </div>
                        <div className="mt-6 text-center">
                            <Button text="Register" color="bg-green-500" style="hover:bg-green-600"/>
                        </div>
                    </form>

                    <div className="mt-10 text-center">
                        <p>
                            {'Already have an account? '}
                            <span className="text-blue-500 hover:underline hover:text-blue-800">
                                <NavLink to="/login" element={<Login/>}>
                                    Sign In
                                </NavLink>
                            </span>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Register