import React from "react";
import { render, cleanup, fireEvent } from '@testing-library/react'
import '@testing-library/jest-dom/extend-expect'
import UserSignUpPage from "./UserSignupPage";

beforeEach(cleanup);

describe('UserSignupPage', () => {
    describe('Layout', () => {
        it('has header of Sign up', () => {
            const { container } = render(<UserSignUpPage />)
            const header = container.querySelector('h1');
            expect(header).toHaveTextContent('Sign up')
        })

        it('has input for display name', () => {
            const { queryByPlaceholderText } = render(<UserSignUpPage />)
            const displayNameInput = queryByPlaceholderText('You display name')
            expect(displayNameInput).toBeInTheDocument
        })

        it('has input for username', () => {
            const { queryByPlaceholderText } = render(<UserSignUpPage />)
            const displayNameInput = queryByPlaceholderText('Your username')
            expect(displayNameInput).toBeInTheDocument()
        })

        it('has input for password', () => {
            const { queryByPlaceholderText } = render(<UserSignUpPage />)
            const displayNameInput = queryByPlaceholderText('Your password')
            expect(displayNameInput).toBeInTheDocument()
        })

        it('has password type for password input', () => {
            const { queryByPlaceholderText } = render(<UserSignUpPage />)
            const displayNameInput = queryByPlaceholderText('Your password') as HTMLInputElement
            expect(displayNameInput.type).toBe('password')
        })
        it('has input for password repeat', () => {
            const { queryByPlaceholderText } = render(<UserSignUpPage />)
            const passwordRepeat = queryByPlaceholderText('Repeat your password')
            expect(passwordRepeat).toBeInTheDocument()
        })

        it('has submit button', () => {
            const { container } = render(<UserSignUpPage />)
            const button = container.querySelector('button')
            expect(button).toBeInTheDocument()
        })

    })

    describe('Interaction', () => {
        const changeEvent = (content: string) => {
            return {
                target: {
                    value: content
                }
            }
        }

        it('sets the desplayName value into state', () => {
            const { queryByPlaceholderText } = render(<UserSignUpPage />)
            const displayNameInput = queryByPlaceholderText('Your display name') as HTMLElement

            fireEvent.change(displayNameInput, changeEvent('my-display-name'));

            expect(displayNameInput).toHaveValue('my-display-name');
        })

        it('sets the username value into state', () => {
            const { queryByPlaceholderText } = render(<UserSignUpPage />)
            const displayNameInput = queryByPlaceholderText('Your username') as HTMLElement

            fireEvent.change(displayNameInput, changeEvent('my-username'));

            expect(displayNameInput).toHaveValue('my-username');
        })

        it('sets the password value into state', () => {
            const { queryByPlaceholderText } = render(<UserSignUpPage />)
            const displayNameInput = queryByPlaceholderText('Your password') as HTMLElement

            fireEvent.change(displayNameInput, changeEvent('my-password'));

            expect(displayNameInput).toHaveValue('my-password');
        })

        it('sets the password repeat value into state', () => {
            const { queryByPlaceholderText } = render(<UserSignUpPage />)
            const displayNameInput = queryByPlaceholderText('Your password repeat') as HTMLElement

            fireEvent.change(displayNameInput, changeEvent('my-password-repeat'));

            expect(displayNameInput).toHaveValue('my-password-repeat');
        })

    })
})