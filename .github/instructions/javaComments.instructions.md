---
applyTo: '**/*.java'
---
Coding standards, domain knowledge, and preferences that AI should follow.
# Java Comments
## General Guidelines
- Use comments to explain the "why" behind complex logic, not the "what" (which should be clear from the code itself).
- Avoid redundant comments that restate what the code does.
- Keep comments concise and relevant to the code they describe.
- Use inline comments sparingly, only when necessary to clarify complex code.
- Use block comments for longer explanations or to describe the purpose of a section of code.
- Ensure comments are updated when the code changes to maintain accuracy.
- Use TODO comments to indicate areas that need further work or improvement, but ensure they are actionable.
- Use FIXME comments to indicate known issues or bugs that need to be addressed.
- For the author use Abdelhamid AKHATAR and abdelhamid.akhatar@etu.cyu.fr

## Comment Types
### Javadoc Comments
- Use Javadoc comments for public classes, methods, and fields.
- Format Javadoc comments with a summary line followed by a more detailed description if necessary.
- Use Javadoc tags like @param, @return, @throws, and @deprecated to provide structured documentation.
- Example:
```java
/**
 * Calculates the sum of two integers.
 * @param a the first integer
 * @param b the second integer
 * @return the sum of a and b
 * @throws IllegalArgumentException if either a or b is negative
 */
public int sum(int a, int b) {
    if (a < 0 || b < 0) {
        throw new IllegalArgumentException("Negative values are not allowed");
    }
    return a + b;
}
```         