import React, { ChangeEvent, SyntheticEvent } from "react";
import { HighlightSpanKind } from "typescript";
import { User } from "../api/apiCalls";

export interface State {
    actions?: {
        postSignup: (user: User) => Promise<any>
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
        pendingApiCall: false
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
        const user: User = {
            username: this.state.username,
            displayName: this.state.displayName,
            password: this.state.password,
        }
        this.setState({ pendingApiCall: true });
        this.props.actions!.postSignup(user)
            .then(() => {
                this.setState({ pendingApiCall: false })
            })
            .catch((error) => {
                this.setState({ pendingApiCall: false })
            });
    }

    render() {
        return (
            <div className="container">
                <h1 className="text-center">Sign up</h1>
                <div className="col-12 mb-3">
                    <label>Display Name</label>
                    <input
                        className="form-control"
                        placeholder="Your display name"
                        value={this.state.displayName}
                        onChange={this.onChangeDisplayName}
                    />
                </div>
                <div className="col-12 mb-3">
                    <label>User Name</label>
                    <input
                        className="form-control"
                        placeholder="Your username"
                        value={this.state.username}
                        onChange={this.onChangeUsername}
                    />
                </div>
                <div className="col-12 mb-3">
                    <label>Password</label>
                    <input
                        className="form-control"
                        placeholder="Your password" type="password"
                        value={this.state.password}
                        onChange={this.onChangePassword}
                    />
                </div>
                <div className="col-12 mb-3">
                    <label>Password Repeat</label>
                    <input
                        className="form-control"
                        placeholder="Repeat your password" type="password"
                        value={this.state.passwordRepeat}
                        onChange={this.onChangePasswordRepeat}
                    />
                </div>
                <div className="text-center">
                    <button
                        className="btn btn-primary"
                        onClick={this.onClickSignup}
                        disabled={this.state.pendingApiCall}
                    >
                        {this.state.pendingApiCall && (
                            <div className="spinner-border text-light spinner-border-sm mr-sm-1" role="status">
                                <span className="visually-hidden">Loading...</span>
                            </div>
                        )}
                        Sign up
                    </button>
                </div>
            </div>
        )
    }
}

export default UserSignUpPage
