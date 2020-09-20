import { Question } from "./question";

export class SubmissionPost {
    
    constructor(
        public title: string,
        public link: string,
        public questions: Array<Question>
    ) {}

}