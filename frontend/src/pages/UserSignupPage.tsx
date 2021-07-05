import React from "react";

interface State {
}

class UserSignUpPage extends React.Component<State> {

    render() {
        return (
            <div>
                <h1>Sign up</h1>
                <div>
                    <input placeholder="Your display name" />
                </div>
                <div>
                    <input placeholder="Your username" />
                </div>
                <div>
                    <input placeholder="Your password" type="password" />
                </div>
                <div>
                    <input placeholder="Repeat your password" type="password" />
                </div>
                <div>
                    <button>Sign up</button>
                </div>
            </div>
        )
    }
}

export default UserSignUpPage