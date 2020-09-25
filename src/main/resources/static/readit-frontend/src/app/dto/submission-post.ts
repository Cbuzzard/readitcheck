import { QuestionPost } from "./question-post";

export class SubmissionPost {
    
    constructor(
        public title: string,
        public link: string,
        public question: QuestionPost
    ) {}

}