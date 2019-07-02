/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { DominioSkillDetailComponent } from 'app/entities/dominio-skill/dominio-skill-detail.component';
import { DominioSkill } from 'app/shared/model/dominio-skill.model';

describe('Component Tests', () => {
  describe('DominioSkill Management Detail Component', () => {
    let comp: DominioSkillDetailComponent;
    let fixture: ComponentFixture<DominioSkillDetailComponent>;
    const route = ({ data: of({ dominioSkill: new DominioSkill(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [DominioSkillDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DominioSkillDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DominioSkillDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dominioSkill).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
