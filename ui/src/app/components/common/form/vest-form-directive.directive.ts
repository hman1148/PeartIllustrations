import {
  AfterViewInit,
  Directive,
  ElementRef,
  EventEmitter,
  HostListener,
  inject,
  Input,
  Output,
  Renderer2,
} from '@angular/core';
import { create } from 'vest';

@Directive({
  selector: `form[vestForm]`,
})
export class VestFormDirective implements AfterViewInit {
  private el = inject(ElementRef);
  private renderer = inject(Renderer2);

  @Input() suite!: (data: unknown, field?: string) => ReturnType<typeof create>;
  @Input() value: unknown;

  @Output() ngOnSubmit = new EventEmitter<unknown>();

  private errorLi: HTMLElement | null = null;

  ngAfterViewInit(): void {
    const form = this.el.nativeElement as HTMLFormElement;
    const inputs = form.querySelectorAll('input, textarea, select');

    inputs.forEach((element: Element) => {
      const input = element as HTMLInputElement;
      input.addEventListener('blur', () => this.validateField(input.name));
      input.addEventListener('input', () => this.validateField(input.name));
    });

    // Create static <li> error below the form
    this.errorLi = this.renderer.createElement('li');
    this.renderer.setStyle(this.errorLi, 'color', 'red');
    this.renderer.setStyle(this.errorLi, 'marginTop', '1rem');
    this.renderer.setProperty(this.errorLi, 'innerText', '');

    this.renderer.appendChild(form, this.errorLi);
  }

  @HostListener('submit', ['$event'])
  onSubmit(event: Event): void {
    event.preventDefault();
    const result = this.suite?.(this.value);
    const errors = this.flattenErrors(result.getErrors());

    this.setError(errors[0] ?? '');

    if (!result.hasErrors()) {
      this.ngOnSubmit.emit(this.value);
    }
  }

  validateField(field: string): void {
    if (!field || !this.suite) return;
    const result = this.suite(this.value, field);
    const errors = this.flattenErrors(result.getErrors());

    this.setError(errors[0] ?? '');
  }

  private setError(message: string) {
    if (this.errorLi) {
      this.renderer.setProperty(this.errorLi, 'innerText', message);
    }
  }

  private flattenErrors(errors: Record<string, string[]>): string[] {
    return Object.values(errors).flat();
  }
}
