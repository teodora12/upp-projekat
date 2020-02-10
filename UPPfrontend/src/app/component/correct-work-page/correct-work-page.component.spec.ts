import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CorrectWorkPageComponent } from './correct-work-page.component';

describe('CorrectWorkPageComponent', () => {
  let component: CorrectWorkPageComponent;
  let fixture: ComponentFixture<CorrectWorkPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CorrectWorkPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CorrectWorkPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
