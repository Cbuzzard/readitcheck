import { UserSimple } from './user-simple'
import { Question } from './question'
import { Comment } from './comment'

export class Submission {

    constructor(
        id: number,
        user: UserSimple,
        title: string,
        link: string,
        question: Question,
        comments: Array<Comment>,
        timestamp: number,
        currentUserApproved: boolean
    ) {}

}