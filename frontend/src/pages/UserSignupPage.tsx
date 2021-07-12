import React, { ChangeEvent, SyntheticEvent } from "react";
import { HighlightSpanKind } from "typescript";

interface State {
    actions?: {
        postSignup: () => Promise<any>
    }
}


class UserSignUpPage extends React.Component<State> {
    static defaultProps = {
        actions: {
            postSignup: () => Promise.resolve({})
        }
    }

    state = {
        displayName: '',
        username: '',
        password: '',
        passwordRepeat: '',
    }


    onChangeDisplayName = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({ displayName: value })
    }

    onChangeUsername = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({ username: value })
    }

    onChangePassword = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({ password: value })
    }

    onChangePasswordRepeat = (event: React.FormEvent<HTMLInputElement>) => {
        const value = event.currentTarget.value;
        this.setState({ passwordRepeat: value })
    }

    onClickSignup = () => {
        if (this.props.actions) {
            this.props.actions.postSignup();
        }
    }

    render() {
        return (
            <div>
                <h1>Sign up</h1>
                <div>
                    <input placeholder="Your display name"
                        value={this.state.displayName}
                        onChange={this.onChangeDisplayName}
                    />
                </div>
                <div>
                    <input placeholder="Your username"
                        value={this.state.username}
                        onChange={this.onChangeUsername}
                    />
                </div>
                <div>
                    <input placeholder="Your password" type="password"
                        value={this.state.password}
                        onChange={this.onChangePassword}
                    />
                </div>
                <div>
                    <input placeholder="Repeat your password" type="password"
                        value={this.state.passwordRepeat}
                        onChange={this.onChangePasswordRepeat}
                    />
                </div>
                <div>
                    <button onClick={this.onClickSignup}>Sign up</button>
                </div>
            </div>
        )
    }
}

export default UserSignUpPage
