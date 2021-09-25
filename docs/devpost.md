## Inspiration

One of the developer wanted to create a project that would solve a real pain
point that he experienced everyday, at work, online and in private circle:

We lost our ability to differentiating between reality and alternative facts,
to acknowledging our limitation, and to being convinced to change opinion.

This leads us to following unhealthy behaviors, to being more fragile
(physically and mentally) and to being less efficient.

To contextualize the challenge, it should be underlined that the four years
preceding it, a pathological liar was the president of the USA, many of his
supporters are denying the reality that he lost the elections, the same people
are refusing to hold for true that a climate crisis caused by human is
happening and that COVID-19 is global dangerous decease and its vaccine is
effective to prevent death.

How does one know that his representation of reality are faithful to it? Which
techniques should be used to verify the veracity of one's knowledge? Is anyone,
even the most intelligent, protected from believing in falsehoods?

Our work try to present the scientific methods as a possible solution to reduce
the risk of believing in the false things.

## What it does

A first part shows that users might be victims a cognitive biases and fallacious
argumentation, and try to show how he can protect himself from these.

A second part is a sketch on how we could gamify the whole process, by rating
our interaction (online, physicial or during a meeting).

## How we built it

We created a front-end web application as we tried to push as much logic and
computation on the client. This has the benefit of being extremely scalable and
to keep the user's privacy.

We use ClojureScript (a powerful lisp language that targets JavaScript),
re-frame and datascript (a datalog in memory database) to manage the state of
the application, reagent and react for displaying components. As for the
visualization, we leveraged on plotly.js. The Google Closure Compiler is then
used to reduce the size of the artifacts and to split the application for
faster loading. We also used `webp` to compress images size.

As for the deployment, we use the free tier of netlify as using git/github as
our devops platform had many benefits given the size of the team and project.

## Challenges we ran into

Making a review of the topic of cognitive bias, fallacies and failures of the
scientific methods in less than 24 hours was challenging.

The topic is also quite difficult to pitch as it is quite abstract.

One of the developper also had for the first time the responsbility to take
care of his baby which limited our ability to develop.

## Accomplishments that we're proud of

It is a functional and responsive website and its structure is near final. We
basically missed the time to fill its content. With a bit of time, it could
become a fine introduction to the scientific methods and make people think
about their believes.

## What we learned

We had to review our content and material several time

- Cognitive bias: everyone can be a victim of their own brain, and it requires
  hard work to reduce its effects. The most frightening observation is that our
  brain compress our memory and when reconstructing memory usually fills the
  gap with the most likely expectations, which might be totally opposite to
  reality.
- Rhetorical and logical fallacies are commonly accepted as valid arguments by
  most leading to sterile discussions.
- Survivalship bias and publication bias for medecine are underestimated and
  that could be public hazard issue.

On the technical side, given the timeframe, we unfortunately did not take any
risks, but then, our tech stack is fairly unconventional anyways.

## What's next for Neo the Doubtful: The Art of Skepticism

We hope we can fill the website with content so that people might actually use
it and learn something out of it. The scientific method is one of the safest
method to discover the truth.

On the technical part, it would be extremely interesting to check whether a NLP
model could classify and annotate fallacies from given sentences. Given the
latest advancement in the domain, we would not be surprised if that worked, and
if yes, if we could leverage tensorflow-js to add it to the website. See
(https://github.com/tensorflow/tfjs-models/tree/master/universal-sentence-encoder)[Universal
Sentence Encoder lite].
